package com.utkarshnew.android.Coupon.Models;

import java.io.Serializable;
import java.util.List;

public class Available implements Serializable {
    String id;
    String image;
    String coupon_tilte;
    String coupon_type;
    String coupon_for;
    String max_usage;
    String max_discount;
    String coupon_value;
    String end;

    public String getExceed_message() {
        return exceed_message;
    }

    public void setExceed_message(String exceed_message) {
        this.exceed_message = exceed_message;
    }

    String exceed_message;


    List<CoursesCoupon> courses;
    List<RedeemJson> redeem_json;

    public List<RedeemJson> getRedeem_json() {
        return redeem_json;
    }

    public void setRedeem_json(List<RedeemJson> redeem_json) {
        this.redeem_json = redeem_json;
    }

    public String getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(String max_discount) {
        this.max_discount = max_discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCoupon_title() {
        return coupon_tilte;
    }

    public void setCoupon_title(String coupon_tilte) {
        this.coupon_tilte = coupon_tilte;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCoupon_for() {
        return coupon_for;
    }

    public void setCoupon_for(String coupon_for) {
        this.coupon_for = coupon_for;
    }

    public String getMax_usage() {
        return max_usage;
    }

    public void setMax_usage(String max_usage) {
        this.max_usage = max_usage;
    }

    public String getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(String coupon_value) {
        this.coupon_value = coupon_value;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<CoursesCoupon> getCourses() {
        return courses;
    }

    public void setCourses(List<CoursesCoupon> courses) {
        this.courses = courses;
    }
}
