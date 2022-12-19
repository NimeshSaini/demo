package com.utkarshnew.android.home.model.Search;

import java.io.Serializable;
import java.util.ArrayList;

public class RecentList implements Serializable {
    ArrayList<RecentData> recentList = null;

    public RecentList(ArrayList<RecentData> recentList) {
        this.recentList = recentList;
    }

    public ArrayList<RecentData> getRecentList() {
        return recentList;
    }

    public void setRecentList(ArrayList<RecentData> recentList) {
        this.recentList = recentList;
    }
}