package com.utkarshnew.android.Webview.Model;

public class FlashDataTerm {

    private String terms;
    private String Q_id;

    public FlashDataTerm(String terms, String q_id) {
        this.terms = terms;
        Q_id = q_id;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getQ_id() {
        return Q_id;
    }

    public void setQ_id(String q_id) {
        Q_id = q_id;
    }
}
