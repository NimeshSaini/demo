package com.utkarshnew.android.courses.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotesType  implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotes_name() {
        return notes_name;
    }

    public void setNotes_name(String notes_name) {
        this.notes_name = notes_name;
    }

    public String getNotes_icon() {
        return notes_icon;
    }

    public void setNotes_icon(String notes_icon) {
        this.notes_icon = notes_icon;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    @SerializedName("type")
    private String id;
    @SerializedName("title")
    private String notes_name;
    @SerializedName("image")
    private String notes_icon;

    @SerializedName("mrp")
    private String mrp;
    @SerializedName("tax")
    private String tax;

    public String getCourse_sp() {
        return course_sp;
    }

    public void setCourse_sp(String course_sp) {
        this.course_sp = course_sp;
    }

    @SerializedName("course_sp")
    private String course_sp;

    public String getAdd_required() {
        return add_required;
    }

    public void setAdd_required(String add_required) {
        this.add_required = add_required;
    }

    @SerializedName("add_required")
    private String add_required;

    public boolean isIs_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    private boolean is_select;
}
