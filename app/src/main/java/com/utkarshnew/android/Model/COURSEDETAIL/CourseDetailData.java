package com.utkarshnew.android.Model.COURSEDETAIL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utkarshnew.android.courses.modal.NotesType;

import java.io.Serializable;
import java.util.List;

public class CourseDetailData implements Serializable {
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cover_image")
    @Expose
    private String cover_image;
    @SerializedName("desc_header_image")
    @Expose
    private String descHeaderImage;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("course_sp")
    @Expose
    private String courseSp;
    @SerializedName("validity")
    @Expose
    private String validity;

    public String getValid_to() {
        return valid_to;
    }

    public void setValid_to(String valid_to) {
        this.valid_to = valid_to;
    }

    @SerializedName("valid_to")
    @Expose
    private String valid_to;
    @SerializedName("is_purchased")
    @Expose
    private String isPurchased;
    @SerializedName("author")
    @Expose
    private Author author;


    public List<NotesType> getNotes_type() {
        return notes_type;
    }

    public void setNotes_type(List<NotesType> notes_type) {
        this.notes_type = notes_type;
    }

    @SerializedName("notes_type")
    @Expose
    private List<NotesType> notes_type;



    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    @SerializedName("view_type")
    @Expose
    private String viewType;

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    @SerializedName("tax")
    @Expose
    private String tax;

    public String getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public String getDescHeaderImage() {
        return descHeaderImage;
    }

    public void setDescHeaderImage(String descHeaderImage) {
        this.descHeaderImage = descHeaderImage;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getCourseSp() {
        return courseSp;
    }

    public void setCourseSp(String courseSp) {
        this.courseSp = courseSp;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(String isPurchased) {
        this.isPurchased = isPurchased;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }
}
