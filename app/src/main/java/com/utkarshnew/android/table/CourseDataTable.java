package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CourseDataTable")
public class CourseDataTable {

    @PrimaryKey(autoGenerate = true)
    private int autoid;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @ColumnInfo(name = "userid")
    private String user_id;

    @ColumnInfo(name = "mainid")
    private String main_id;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "courseid")
    private String course_id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "cover_image")
    private String cover_image;

    @ColumnInfo(name = "mrp")
    private String mrp;


    @ColumnInfo(name = "course_sp")
    private String course_sp;

    @ColumnInfo(name = "subject_id")
    private String subject_id;

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getLang_id() {
        return lang_id;
    }

    public void setLang_id(String lang_id) {
        this.lang_id = lang_id;
    }

    @ColumnInfo(name = "lang_id")
    private String lang_id;


    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getMain_id() {
        return main_id;
    }

    public void setMain_id(String main_id) {
        this.main_id = main_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
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

    public String getCourse_sp() {
        return course_sp;
    }

    public void setCourse_sp(String course_sp) {
        this.course_sp = course_sp;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    @ColumnInfo(name = "validity")
    private String validity;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    @ColumnInfo(name = "type_id")
    private String type_id;


    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    @ColumnInfo(name = "valid_to")
    private String valid_to;
}
