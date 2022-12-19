
package com.utkarshnew.android.Model.Courses;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyDoc {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("course_topic_master_id")
    @Expose
    private String courseTopicMasterId;
    @SerializedName("sub_stream_id")
    @Expose
    private String subStreamId;
    @SerializedName("pdf_data")
    @Expose
    private ArrayList<PdfDatum> pdfData = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseTopicMasterId() {
        return courseTopicMasterId;
    }

    public void setCourseTopicMasterId(String courseTopicMasterId) {
        this.courseTopicMasterId = courseTopicMasterId;
    }

    public String getSubStreamId() {
        return subStreamId;
    }

    public void setSubStreamId(String subStreamId) {
        this.subStreamId = subStreamId;
    }

    public ArrayList<PdfDatum> getPdfData() {
        return pdfData;
    }

    public void setPdfData(ArrayList<PdfDatum> pdfData) {
        this.pdfData = pdfData;
    }

}
