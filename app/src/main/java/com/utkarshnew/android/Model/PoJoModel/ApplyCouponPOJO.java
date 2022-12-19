
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class ApplyCouponPOJO implements Serializable {

    private String course_id;
    private String coupon_code;
    private String emi_no;

    public ApplyCouponPOJO(String course_id, String coupon_code, String emi_no) {
        this.course_id = course_id;
        this.coupon_code = coupon_code;
        this.emi_no = emi_no;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getEmi_no() {
        return emi_no;
    }

    public void setEmi_no(String emi_no) {
        this.emi_no = emi_no;
    }
}
