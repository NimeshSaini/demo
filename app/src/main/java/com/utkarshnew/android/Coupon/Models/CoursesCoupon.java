package com.utkarshnew.android.Coupon.Models;

import java.io.Serializable;

public class CoursesCoupon implements Serializable {
    String id;
    String title;
    String cover_image;
    String mrp;
    String tax;
    String final_mrp;
    String discount;
    String course_sp;
    String validity;

    public String getIs_purchased() {
        return is_purchased;
    }

    public void setIs_purchased(String is_purchased) {
        this.is_purchased = is_purchased;
    }

    String is_purchased;

    Available coupon;

    public boolean isIs_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    boolean is_select;


    public Available getCoupon() {
        return coupon;
    }

    public void setCoupon(Available coupon) {
        this.coupon = coupon;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getFinal_mrp() {
        return final_mrp;
    }

    public void setFinal_mrp(String final_mrp) {
        this.final_mrp = final_mrp;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCourse_sp() {
        return course_sp;
    }

    public void setCourse_sp(String course_sp) {
        this.course_sp = course_sp;
    }

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

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }
}
