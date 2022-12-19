
package com.utkarshnew.android.Model.TestPDFData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestPDFQuiz implements Serializable {

    @SerializedName("test_json")
    @Expose
    private TestJson testJson;

    public TestJson getTestJson() {
        return testJson;
    }

    public void setTestJson(TestJson testJson) {
        this.testJson = testJson;
    }

}
