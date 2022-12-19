package com.utkarshnew.android.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("top_menu")
    @Expose
    private List<Menu> topMenu;

    @SerializedName("bottom_menu")
    @Expose
    private List<Menu> bottomMenu;

    @SerializedName("left_menu")
    @Expose
    private List<Menu> leftMenu;


    public List<Menu> getTopMenu() {
        return topMenu;
    }

    public List<Menu> getBottomMenu() {
        return bottomMenu;
    }

    public List<Menu> getLeftMenu() {
        return leftMenu;
    }

}
