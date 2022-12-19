package com.utkarshnew.android.Model.NotificationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("message")
    @Expose
    private String message;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("title")
    @Expose
    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("action_element")
    @Expose
    private String actionElement;
    @SerializedName("action_element_id")
    @Expose
    private String actionElementId;
    @SerializedName("extra")
    @Expose
    private Extras extra = null;
    @SerializedName("view_state")
    @Expose
    private String viewState;
    @SerializedName("created")
    @Expose
    private String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getActionElement() {
        return actionElement;
    }

    public void setActionElement(String actionElement) {
        this.actionElement = actionElement;
    }

    public String getActionElementId() {
        return actionElementId;
    }

    public void setActionElementId(String actionElementId) {
        this.actionElementId = actionElementId;
    }

    public Extras getExtra() {
        return extra;
    }

    public void setExtra(Extras extra) {
        this.extra = extra;
    }

    public String getViewState() {
        return viewState;
    }

    public void setViewState(String viewState) {
        this.viewState = viewState;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
