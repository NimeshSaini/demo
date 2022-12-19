package com.utkarshnew.android.home.model.myNotesData;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyNotes implements Parcelable {
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("transaction_mode")
    @Expose
    private String transactionMode;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("amount_paid")
    @Expose
    private String amountPaid;
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("course_attribute")
    @Expose
    private String courseAttribute;
    @SerializedName("cover_image")
    @Expose
    private String coverImage;
    @SerializedName("desc_header_image")
    @Expose
    private String descHeaderImage;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("course_sp")
    @Expose
    private String courseSp;
    @SerializedName("color_code")
    @Expose
    private String colorCode;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("course_type")
    @Expose
    private String courseType;
    @SerializedName("invoice_url")
    @Expose
    private String invoiceUrl;

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseAttribute() {
        return courseAttribute;
    }

    public void setCourseAttribute(String courseAttribute) {
        this.courseAttribute = courseAttribute;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    protected MyNotes(Parcel in) {
        transactionStatus = in.readString();
        transactionMode = in.readString();
        transactionId = in.readString();
        amountPaid = in.readString();
        transactionDate = in.readString();
        courseId = in.readString();
        title = in.readString();
        courseAttribute = in.readString();
        coverImage = in.readString();
        descHeaderImage = in.readString();
        mrp = in.readString();
        courseSp = in.readString();
        colorCode = in.readString();
        validity = in.readString();
        courseType = in.readString();
        invoiceUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(transactionStatus);
        dest.writeString(transactionMode);
        dest.writeString(transactionId);
        dest.writeString(amountPaid);
        dest.writeString(transactionDate);
        dest.writeString(courseId);
        dest.writeString(title);
        dest.writeString(courseAttribute);
        dest.writeString(coverImage);
        dest.writeString(descHeaderImage);
        dest.writeString(mrp);
        dest.writeString(courseSp);
        dest.writeString(colorCode);
        dest.writeString(validity);
        dest.writeString(courseType);
        dest.writeString(invoiceUrl);
    }

    @SuppressWarnings("unused")
    public static final Creator<MyNotes> CREATOR = new Creator<MyNotes>() {
        @Override
        public MyNotes createFromParcel(Parcel in) {
            return new MyNotes(in);
        }

        @Override
        public MyNotes[] newArray(int size) {
            return new MyNotes[size];
        }
    };
}