package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class PopupDataCollectionPOJO implements Serializable {
    String popup_id;
    String course_id;

    public PopupDataCollectionPOJO(String popup_id, String course_id) {
        this.popup_id = popup_id;
        this.course_id = course_id;
    }

    public String getPopup_id() {
        return popup_id;
    }

    public void setPopup_id(String popup_id) {
        this.popup_id = popup_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
}
