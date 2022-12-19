package com.utkarshnew.android.table;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class HomeApiStatusTable {

    @PrimaryKey(autoGenerate = true)
    private int autoid;

    @ColumnInfo(name = "userid")
    private String user_id;

    @ColumnInfo(name = "mainid")
    private String main_id;

    public int getAutoid() {
        return autoid;
    }

    public void setAutoid(int autoid) {
        this.autoid = autoid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMain_id() {
        return main_id;
    }

    public void setMain_id(String main_id) {
        this.main_id = main_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ColumnInfo(name = "status")
    private String status;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @ColumnInfo(name = "page")
    private String page;
}
