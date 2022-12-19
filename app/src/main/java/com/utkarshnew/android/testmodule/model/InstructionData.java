package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InstructionData implements Serializable {
    @SerializedName("test_basic")
    @Expose
    private TestBasicInst testBasic;
    @SerializedName("test_sections")
    @Expose
    private List<TestSectionInst> testSections = null;

    public TestBasicInst getTestBasic() {
        return testBasic;
    }

    public void setTestBasic(TestBasicInst testBasic) {
        this.testBasic = testBasic;
    }

    public List<TestSectionInst> getTestSections() {
        return testSections;
    }

    public void setTestSections(List<TestSectionInst> testSections) {
        this.testSections = testSections;
    }
}