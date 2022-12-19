package com.utkarshnew.android.Response.Registration;

/**
 * Created by Cbc-03 on 06/07/17.
 */

public class SubStreamResponse extends StreamResponse {
    private String parent_id;

    private String subject_ids;

    public String getSubject_ids() {
        return subject_ids;
    }

    public void setSubject_ids(String subject_ids) {
        this.subject_ids = subject_ids;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
