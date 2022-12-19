package com.utkarshnew.android.Model;

import java.io.Serializable;

public class FlashData implements Serializable {
    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String terms;
    private String description;
    private String Q_id;

    public String getQ_id() {
        return Q_id;
    }

    public void setQ_id(String q_id) {
        Q_id = q_id;
    }

    public boolean isIs_selectecd() {
        return is_selectecd;
    }

    public void setIs_selectecd(boolean is_selectecd) {
        this.is_selectecd = is_selectecd;
    }

    private boolean is_selectecd = false;
}
