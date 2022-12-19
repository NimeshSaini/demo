package com.utkarshnew.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PopupData implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("course_product")
    @Expose
    private String courseProduct;
    @SerializedName("gift_course")
    @Expose
    private String giftCourse;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("enable_status")
    @Expose
    private String enable_status;
    @SerializedName("popup_pass")
    @Expose
    private String popupPass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourseProduct() {
        return courseProduct;
    }

    public void setCourseProduct(String courseProduct) {
        this.courseProduct = courseProduct;
    }

    public String getGiftCourse() {
        return giftCourse;
    }

    public void setGiftCourse(String giftCourse) {
        this.giftCourse = giftCourse;
    }

    public String getPopupPass() {
        return popupPass;
    }

    public void setPopupPass(String popupPass) {
        this.popupPass = popupPass;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEnable_status() {
        return enable_status;
    }

    public void setEnable_status(String enable_status) {
        this.enable_status = enable_status;
    }
}
