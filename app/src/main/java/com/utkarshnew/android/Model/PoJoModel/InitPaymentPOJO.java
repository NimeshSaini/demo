
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class InitPaymentPOJO implements Serializable {

    private String course_id;
    private String points_rate;
    private String tax;
    private String pay_via;
    private String points_used;
    private String course_price;
    private String address;
    private String coupon_applied;
    private String emi_no;

    public InitPaymentPOJO(String course_id, String points_rate, String tax, String pay_via, String points_used, String course_price, String address, String coupon_applied, String emi_no) {
        this.course_id = course_id;
        this.points_rate = points_rate;
        this.tax = tax;
        this.pay_via = pay_via;
        this.points_used = points_used;
        this.course_price = course_price;
        this.address = address;
        this.coupon_applied = coupon_applied;
        this.emi_no = emi_no;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getPoints_rate() {
        return points_rate;
    }

    public void setPoints_rate(String points_rate) {
        this.points_rate = points_rate;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getPay_via() {
        return pay_via;
    }

    public void setPay_via(String pay_via) {
        this.pay_via = pay_via;
    }

    public String getPoints_used() {
        return points_used;
    }

    public void setPoints_used(String points_used) {
        this.points_used = points_used;
    }

    public String getCourse_price() {
        return course_price;
    }

    public void setCourse_price(String course_price) {
        this.course_price = course_price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoupon_applied() {
        return coupon_applied;
    }

    public void setCoupon_applied(String coupon_applied) {
        this.coupon_applied = coupon_applied;
    }

    public String getEmi_no() {
        return emi_no;
    }

    public void setEmi_no(String emi_no) {
        this.emi_no = emi_no;
    }
}
