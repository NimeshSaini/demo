package com.utkarshnew.android.Webview.Model;

public class FlashDataDescription {

    private String description;
    private String Q_id;

    public FlashDataDescription(String description, String q_id) {
        this.description = description;
        Q_id = q_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQ_id() {
        return Q_id;
    }

    public void setQ_id(String q_id) {
        Q_id = q_id;
    }
}
