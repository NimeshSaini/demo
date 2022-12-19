package com.utkarshnew.android.Model.Courses;

import java.io.Serializable;
import java.util.List;

public class Basic implements Serializable {
    private String image_icon;

    private String id;

    private String title;

    private String title_2;

    private String course_sp;

    private String desc_header_image;

    private String description;

    private String validity;

    private String payment_type;

    private String color_code;

    private String is_purchased;

    private String mrp;

    private String course_attribute;

    private String l_text;

    private String r_text;

    private String is_postal_available;

    private String holderType;

    private String combo_course_ids;

    private List<EMIInfo> emi_prices = null;

    public Basic(String image_icon, String id, String title, String course_sp, String desc_header_image, String description, String validity, String payment_type, String color_code, String is_purchased, String mrp, String course_attribute, String l_text, String r_text, String is_postal_available, String holderType, String combo_course_ids, List<EMIInfo> emi_prices) {
        this.image_icon = image_icon;
        this.id = id;
        this.title = title;
        this.course_sp = course_sp;
        this.desc_header_image = desc_header_image;
        this.description = description;
        this.validity = validity;
        this.payment_type = payment_type;
        this.color_code = color_code;
        this.is_purchased = is_purchased;
        this.mrp = mrp;
        this.course_attribute = course_attribute;
        this.l_text = l_text;
        this.r_text = r_text;
        this.is_postal_available = is_postal_available;
        this.holderType = holderType;
        this.combo_course_ids = combo_course_ids;
        this.emi_prices = emi_prices;
    }

    public Basic(String image_icon, String id, String title, String course_sp, String desc_header_image, String description, String validity, String payment_type, String color_code, String is_purchased, String mrp, String course_attribute, String l_text, String r_text, String is_postal_available, String holderType, String combo_course_ids) {
        this.image_icon = image_icon;
        this.id = id;
        this.title = title;
        this.course_sp = course_sp;
        this.desc_header_image = desc_header_image;
        this.description = description;
        this.validity = validity;
        this.payment_type = payment_type;
        this.color_code = color_code;
        this.is_purchased = is_purchased;
        this.mrp = mrp;
        this.course_attribute = course_attribute;
        this.l_text = l_text;
        this.r_text = r_text;
        this.is_postal_available = is_postal_available;
        this.holderType = holderType;
        this.combo_course_ids = combo_course_ids;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_icon() {
        return image_icon;
    }

    public void setImage_icon(String image_icon) {
        this.image_icon = image_icon;
    }

    public String getId() {
        return id;
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

    public String getTitle_2() {
        return title_2;
    }

    public void setTitle_2(String title_2) {
        this.title_2 = title_2;
    }

    public String getCourse_sp() {
        return course_sp;
    }

    public void setCourse_sp(String course_sp) {
        this.course_sp = course_sp;
    }

    public String getDesc_header_image() {
        return desc_header_image;
    }

    public void setDesc_header_image(String desc_header_image) {
        this.desc_header_image = desc_header_image;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    public String getIs_purchased() {
        return is_purchased;
    }

    public void setIs_purchased(String is_purchased) {
        this.is_purchased = is_purchased;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getCourse_attribute() {
        return course_attribute;
    }

    public void setCourse_attribute(String course_attribute) {
        this.course_attribute = course_attribute;
    }

    public String getL_text() {
        return l_text;
    }

    public void setL_text(String l_text) {
        this.l_text = l_text;
    }

    public String getR_text() {
        return r_text;
    }

    public void setR_text(String r_text) {
        this.r_text = r_text;
    }

    public String getIs_postal_available() {
        return is_postal_available;
    }

    public void setIs_postal_available(String is_postal_available) {
        this.is_postal_available = is_postal_available;
    }

    public String getHolderType() {
        return holderType;
    }

    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }

    public String getCombo_course_ids() {
        return combo_course_ids;
    }

    public void setCombo_course_ids(String combo_course_ids) {
        this.combo_course_ids = combo_course_ids;
    }

    public List<EMIInfo> getEmiPrices() {
        return emi_prices;
    }

    public void setEmiPrices(List<EMIInfo> emi_prices) {
        this.emi_prices = emi_prices;
    }
}
