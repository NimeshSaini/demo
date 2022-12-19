
package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class LandingPagePOJO implements Serializable {

    private String course_type;
    private String main_cat;
    private String study_type;

    public LandingPagePOJO(String course_type, String main_cat, String study_type) {
        this.course_type = course_type;
        this.main_cat = main_cat;
        this.study_type = study_type;
    }

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getMain_cat() {
        return main_cat;
    }

    public void setMain_cat(String main_cat) {
        this.main_cat = main_cat;
    }

    public String getStudy_type() {
        return study_type;
    }

    public void setStudy_type(String study_type) {
        this.study_type = study_type;
    }
}
