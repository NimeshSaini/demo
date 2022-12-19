package com.utkarshnew.android.Model.COURSEDETAIL;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    private List<TilesItem> tiles;
    @SerializedName("course_detail")
    private CourseDetailData courseDetail;

    public List<TilesItem> getTiles() {
        return tiles;
    }

    public CourseDetailData getCourseDetail() {
        return courseDetail;
    }

    public void setTiles(List<TilesItem> tiles) {
        this.tiles = tiles;
    }

    public void setCourseDetail(CourseDetailData courseDetail) {
        this.courseDetail = courseDetail;
    }
}