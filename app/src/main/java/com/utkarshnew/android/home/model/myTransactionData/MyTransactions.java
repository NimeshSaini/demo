package com.utkarshnew.android.home.model.myTransactionData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyTransactions implements Serializable {
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
    @SerializedName("holder_type")
    @Expose
    private String holderType;
    @SerializedName("delivery_status")
    @Expose
    private String deliveryStatus;
    @SerializedName("delivery_json")
    @Expose
    private List<DeliveryJson> deliveryJson = null;

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public List<DeliveryJson> getDeliveryJson() {
        return deliveryJson;
    }

    public void setDeliveryJson(List<DeliveryJson> deliveryJson) {
        this.deliveryJson = deliveryJson;
    }


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

    public String getHolderType() {
        return holderType;
    }

    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }
}