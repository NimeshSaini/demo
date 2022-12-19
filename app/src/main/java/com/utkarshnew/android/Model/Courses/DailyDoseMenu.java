package com.utkarshnew.android.Model.Courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DailyDoseMenu implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("child_type")
    @Expose
    private String child_type;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("course_type_master_id")
    @Expose
    private String course_type_master_id;

    @SerializedName("c_code")
    @Expose
    private String c_code;

    @SerializedName("target")
    @Expose
    private String target;

    @SerializedName("web_url")
    @Expose
    private String web_url;

    @SerializedName("dose_id")
    @Expose
    private String dose_id;

    @SerializedName("parent_id")
    @Expose
    private String parent_id;

    public String getDose_id() {
        return dose_id;
    }

    public void setDose_id(String dose_id) {
        this.dose_id = dose_id;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
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

    public String getChild_type() {
        return child_type;
    }

    public void setChild_type(String child_type) {
        this.child_type = child_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCourse_type_master_id() {
        return course_type_master_id;
    }

    public void setCourse_type_master_id(String course_type_master_id) {
        this.course_type_master_id = course_type_master_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "DailyDoseMenu{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", child_type='" + child_type + '\'' +
                ", image='" + image + '\'' +
                ", course_type_master_id='" + course_type_master_id + '\'' +
                ", c_code='" + c_code + '\'' +
                ", target='" + target + '\'' +
                ", web_url='" + web_url + '\'' +
                ", dose_id='" + dose_id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                '}';
    }
}
