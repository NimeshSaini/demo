package com.utkarshnew.android.Model;

import java.io.Serializable;

public class Categoryinfo implements Serializable {

    private String id;

    private String color;

    private String font_color;

    private String stream_color;

    private String status;

    private String name_2;

    private String name;

    private String creation_time;

    private String image;

    private String last_updated;

    private String type;

    private String parent_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor_font() {
        return font_color;
    }

    public void setColor_font(String font_color) {
        this.font_color = font_color;
    }

    public String getStream_color() {
        return stream_color;
    }

    public void setStream_color(String stream_color) {
        this.stream_color = stream_color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName_2() {
        return name_2;
    }

    public void setName_2(String name_2) {
        this.name_2 = name_2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public Categoryinfo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", color = " + color + ", status = " + status + ", name_2 = " + name_2 + ", name = " + name + ", creation_time = " + creation_time + ", image = " + image + ", last_updated = " + last_updated + ", type = " + type + ", parent_id = " + parent_id + "]";
    }
}
