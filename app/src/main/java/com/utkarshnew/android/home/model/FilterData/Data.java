
package com.utkarshnew.android.home.model.FilterData;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utkarshnew.android.table.LanguagesTable;


public class Data {

    @SerializedName("languages")
    @Expose
    private List<LanguagesTable> languages = null;
    @SerializedName("subjects")
    @Expose
    private List<Subject> subjects = null;

    public List<LanguagesTable> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguagesTable> languages) {
        this.languages = languages;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

}
