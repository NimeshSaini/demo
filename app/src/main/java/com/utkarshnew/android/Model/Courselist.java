package com.utkarshnew.android.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Courselist implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;


    public Courselist() {

    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    @SerializedName("txn_id")
    @Expose
    private String txn_id;


    @SerializedName("view_type")
    @Expose
    private String viewType;
    @SerializedName("holder_type")
    @Expose
    private String holderType;
    @SerializedName("maintenance_text")
    @Expose
    private String maintenanceText;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("segment_information")
    @Expose
    private String segment_information;
    @SerializedName("course_attribute")
    @Expose
    private String courseAttribute;

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

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
    @SerializedName("color_code")
    @Expose
    private String colorCode;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("learner")
    @Expose
    private String learner;

    @SerializedName("is_live")
    @Expose
    private String isLive;
    private String is_locked;
    private boolean isExpand = false;

    public String getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(String is_locked) {
        this.is_locked = is_locked;
    }

    public String getLastread() {
        return lastread;
    }

    public void setLastread(String lastread) {
        this.lastread = lastread;
    }

    @SerializedName("lastread")
    @Expose
    private String lastread;
    @SerializedName("skip_unit")
    @Expose
    private String skipUnit;
    @SerializedName("skip_chapter")
    @Expose
    private String skipChapter;

    public String getCombo_course_ids() {
        return combo_course_ids;
    }

    public void setCombo_course_ids(String combo_course_ids) {
        this.combo_course_ids = combo_course_ids;
    }

    @SerializedName("combo_course_ids")
    @Expose
    private String combo_course_ids;
    @SerializedName("payment_type")
    @Expose
    private String payment_type;
    @SerializedName("is_postal_available")
    @Expose
    private String is_postal_available;
    @SerializedName("expiry_date")
    @Expose
    private String expiry_date;
    @SerializedName("purchase_date")
    @Expose
    private String purchase_date;
    @SerializedName("url")
    @Expose
    private String url;

    public List<ExtendValidity> getPrices() {
        return prices;
    }

    public void setPrices(List<ExtendValidity> prices) {
        this.prices = prices;
    }


    @SerializedName("prices")
    @Expose
    private List<ExtendValidity> prices;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect = false;

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    @SerializedName("batch_id")
    @Expose
    private String batch_id;


    public Courselist(String id, String viewType, String holderType, String maintenanceText, String title, String courseAttribute, String cover_image, String descHeaderImage, String mrp, String courseSp, String colorCode, String validity, String learner, String isLive, String url) {
        this.id = id;
        this.viewType = viewType;
        this.holderType = holderType;
        this.maintenanceText = maintenanceText;
        this.title = title;
        this.courseAttribute = courseAttribute;
        this.cover_image = cover_image;
        this.descHeaderImage = descHeaderImage;
        this.mrp = mrp;
        this.courseSp = courseSp;
        this.colorCode = colorCode;
        this.validity = validity;
        this.learner = learner;
        this.isLive = isLive;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getHolderType() {
        return holderType;
    }

    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }

    public String getMaintenanceText() {
        return maintenanceText;
    }

    public void setMaintenanceText(String maintenanceText) {
        this.maintenanceText = maintenanceText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSegment_information() {
        return segment_information;
    }

    public void setSegment_information(String segment_information) {
        this.segment_information = segment_information;
    }

    public String getCourseAttribute() {
        return courseAttribute;
    }

    public void setCourseAttribute(String courseAttribute) {
        this.courseAttribute = courseAttribute;
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

    public String getLearner() {
        return learner;
    }

    public void setLearner(String learner) {
        this.learner = learner;
    }


    public String getIsLive() {
        return isLive;
    }

    public void setIsLive(String isLive) {
        this.isLive = isLive;
    }

    public String getSkipUnit() {
        return skipUnit;
    }

    public void setSkipUnit(String skipUnit) {
        this.skipUnit = skipUnit;
    }

    public String getSkipChapter() {
        return skipChapter;
    }

    public void setSkipChapter(String skipChapter) {
        this.skipChapter = skipChapter;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getIs_postal_available() {
        return is_postal_available;
    }

    public void setIs_postal_available(String is_postal_available) {
        this.is_postal_available = is_postal_available;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }


    public Courselist(String id, String title, String cover_image, String mrp, String courseSp, String validity, String subjectid, String lang) {
        this.id = id;
        this.title = title;
        this.cover_image = cover_image;
        this.mrp = mrp;
        this.courseSp = courseSp;
        this.validity = validity;
        this.subject_id = subjectid;
        this.lang_id = lang;
    }


    public String getLang_id() {
        return lang_id;
    }

    public void setLang_id(String lang_id) {
        this.lang_id = lang_id;
    }

    @SerializedName("lang_id")
    @Expose
    private String lang_id;


    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    @SerializedName("subject_id")
    @Expose
    private String subject_id;


    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    @SerializedName("type_id")
    @Expose
    private String type_id;

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    @SerializedName("delete")
    @Expose
    private int delete;
}