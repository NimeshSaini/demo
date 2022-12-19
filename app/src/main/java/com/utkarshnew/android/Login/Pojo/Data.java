package com.utkarshnew.android.Login.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("contact")
    @Expose
    private List<Long> contact = null;
    @SerializedName("course")
    @Expose
    private List<String> course = null;
    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("enrollmentno")
    @Expose
    private List<String> enrollmentno = null;
    @SerializedName("sku")
    @Expose
    private List<String> sku = null;
    @SerializedName("sname")
    @Expose
    private List<String> sname = null;
    @SerializedName("otp")
    @Expose
    private Integer otp;

    public List<Long> getContact() {
        return contact;
    }

    public void setContact(List<Long> contact) {
        this.contact = contact;
    }

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getEnrollmentno() {
        return enrollmentno;
    }

    public void setEnrollmentno(List<String> enrollmentno) {
        this.enrollmentno = enrollmentno;
    }

    public List<String> getSku() {
        return sku;
    }

    public void setSku(List<String> sku) {
        this.sku = sku;
    }

    public List<String> getSname() {
        return sname;
    }

    public void setSname(List<String> sname) {
        this.sname = sname;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

}