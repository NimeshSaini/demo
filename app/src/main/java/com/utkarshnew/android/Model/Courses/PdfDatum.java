
package com.utkarshnew.android.Model.Courses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PdfDatum {

    @SerializedName("schedule_url")
    @Expose
    private String scheduleUrl;
    @SerializedName("syllabus_url")
    @Expose
    private String syllabusUrl;

    public String getScheduleUrl() {
        return scheduleUrl;
    }

    public void setScheduleUrl(String scheduleUrl) {
        this.scheduleUrl = scheduleUrl;
    }

    public String getSyllabusUrl() {
        return syllabusUrl;
    }

    public void setSyllabusUrl(String syllabusUrl) {
        this.syllabusUrl = syllabusUrl;
    }

}
