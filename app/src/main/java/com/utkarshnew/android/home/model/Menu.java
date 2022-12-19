package com.utkarshnew.android.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("parent_id")
    @Expose
    private String parentId;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name1")
    @Expose
    private String name1;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("description1")
    @Expose
    private String description1;
    @SerializedName("link_level")
    @Expose
    private String linkLevel;

    @SerializedName("web_link")
    @Expose
    private String webLink;
    @SerializedName("study_type")
    @Expose
    private String studyType;
    @SerializedName("study_type_detail")
    @Expose
    private String studyTypeDetail;
    @SerializedName("feed_type")
    @Expose
    private String feedType;

    @SerializedName("menu_type_id")
    @Expose
    private String menuTypeId;

    @SerializedName("have_child")
    @Expose
    private String haveChild;

    @SerializedName("image")
    @Expose
    private int image;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("modified")
    @Expose
    private String modified;

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("type_name")
    @Expose
    private String typeName;

    @SerializedName("type_code")
    @Expose
    private String type_code;


    @SerializedName("sub_menu")
    @Expose
    private List<Menu> subMenu;

    private boolean selected = false;
    private boolean expanded = false;

    public Menu(String type_code) {
        this.type_code = type_code;
    }

    public Menu() {
    }

    public Menu(String id, String type_code) {
        this.id = id;
        this.type_code = type_code;
    }

    public Menu(String id, String parentId, String name, String name1, String menuTypeId, String haveChild, int image, String type_code, List<Menu> subMenu) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.name1 = name1;
        this.menuTypeId = menuTypeId;
        this.haveChild = haveChild;
        this.image = image;
        this.type_code = type_code;
        this.subMenu = subMenu;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getLinkLevel() {
        return linkLevel;
    }

    public void setLinkLevel(String linkLevel) {
        this.linkLevel = linkLevel;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getStudyTypeDetail() {
        return studyTypeDetail;
    }

    public void setStudyTypeDetail(String studyTypeDetail) {
        this.studyTypeDetail = studyTypeDetail;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getMenuTypeId() {
        return menuTypeId;
    }

    public String getHaveChild() {
        return haveChild;
    }

    public int getImage() {
        return image;
    }

    public String getPosition() {
        return position;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<Menu> getSubMenu() {
        return subMenu;
    }

    public String getType_code() {
        return type_code;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
