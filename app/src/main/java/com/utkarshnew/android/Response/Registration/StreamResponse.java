package com.utkarshnew.android.Response.Registration;

import java.io.Serializable;

/**
 * Created by Cbc-03 on 06/07/17.
 */

public class StreamResponse implements Serializable {

    private String id;

    private String visibilty;

    private String text;

    private String image;

    private String color;

    public StreamResponse() {
    }

    public StreamResponse(String id, String visibilty, String text, String image, String color) {
        this.id = id;
        this.visibilty = visibilty;
        this.text = text;
        this.image = image;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisibilty() {
        return visibilty;
    }

    public void setVisibilty(String visibilty) {
        this.visibilty = visibilty;
    }

    public String getText_name() {
        return text;
    }

    public void setText_name(String text) {
        this.text = text;
    }
}
