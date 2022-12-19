package com.utkarshnew.android.Model.Courses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DailyDoseWrapper implements Serializable {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("dose_menu")
    @Expose
    private ArrayList<DailyDoseMenu> doseMenu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DailyDoseMenu> getDoseMenu() {
        return doseMenu;
    }

    public void setDoseMenu(ArrayList<DailyDoseMenu> doseMenu) {
        this.doseMenu = doseMenu;
    }


}
