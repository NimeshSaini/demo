package com.utkarshnew.android.courses.modal.NotesPDF;

import java.io.Serializable;

public class NoteData implements Serializable {
    String userId;
    String conceptId;
    String queryData;
    String title;
    String type;
    int start;
    int end;
    private boolean isSelect = false;

    public NoteData() {

    }

    public NoteData(String userId, String conceptId, String queryData, String title, String type, int start, int end) {
        this.userId = userId;
        this.conceptId = conceptId;
        this.queryData = queryData;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public String getQueryData() {
        return queryData;
    }

    public void setQueryData(String queryData) {
        this.queryData = queryData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
