package com.utkarshnew.android.testmodule.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SectioHashMap  {


    public int getSectioncount() {
        return sectioncount;
    }

    public void setSectioncount(int sectioncount) {
        this.sectioncount = sectioncount;
    }

    public int getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(int optionCount) {
        this.optionCount = optionCount;
    }

    public int  sectioncount;
    public int optionCount;
}