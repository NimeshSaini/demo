
package com.utkarshnew.android.Model.Courses;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyAnalytics {

    @SerializedName("study_doc")
    @Expose
    private ArrayList<StudyDoc> studyDoc = null;
    @SerializedName("graph_data")
    @Expose
    private ArrayList<GraphDatum> graphData = null;

    public ArrayList<StudyDoc> getStudyDoc() {
        return studyDoc;
    }

    public void setStudyDoc(ArrayList<StudyDoc> studyDoc) {
        this.studyDoc = studyDoc;
    }

    public ArrayList<GraphDatum> getGraphData() {
        return graphData;
    }

    public void setGraphData(ArrayList<GraphDatum> graphData) {
        this.graphData = graphData;
    }

}
