package com.utkarshnew.android.home.model.Search;

import java.io.Serializable;

public class RecentData implements Serializable {
    String userId;
    String queryData;

    public RecentData(String userId, String queryData) {
        this.userId = userId;
        this.queryData = queryData;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQueryData() {
        return queryData;
    }

    public void setQueryData(String queryData) {
        this.queryData = queryData;
    }
}
