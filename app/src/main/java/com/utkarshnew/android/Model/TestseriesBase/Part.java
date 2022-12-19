
package com.utkarshnew.android.Model.TestseriesBase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Part implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("test_series_id")
    @Expose
    private String testSeriesId;
    @SerializedName("part_name")
    @Expose
    private String partName;

    public int getIndexOf() {
        return indexOf;
    }

    public void setIndexOf(int indexOf) {
        this.indexOf = indexOf;
    }

    private int indexOf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestSeriesId() {
        return testSeriesId;
    }

    public void setTestSeriesId(String testSeriesId) {
        this.testSeriesId = testSeriesId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public void setIndex(int indexOf) {
        this.indexOf = indexOf;
    }
}
