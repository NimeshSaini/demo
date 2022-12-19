package com.utkarshnew.android.Login.Pojo;

import java.io.Serializable;

/**
 * Created by appsquadz on 4/26/2018.
 */

public class ExamCategory implements Serializable {

    private String position;

    private String id;

    private String title;

    private String image_url;

    private String parent_id;

    private String description;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override

    public String toString() {
        return "ClassPojo [position = " + position + ", id = " + id + ", description = " + description + ", title = " + title + ", image_url = " + image_url + ", parent_id = " + parent_id + "]";
    }
}
