package com.utkarshnew.android.courses.modal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TopicData implements Serializable {

    public boolean Expanded = false;

    public boolean isSetExpanded() {
        return Expanded;
    }

    public void setSetExpanded(boolean expanded) {
        Expanded = expanded;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("color_code")
    @Expose
    private String colorCode;
    @SerializedName("sub_topic")
    @Expose
    private List<SubTopic> subTopic = null;

    @SerializedName("is_live")
    @Expose
    private String is_live;

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public List<SubTopic> getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(List<SubTopic> subTopic) {
        this.subTopic = subTopic;
    }

}