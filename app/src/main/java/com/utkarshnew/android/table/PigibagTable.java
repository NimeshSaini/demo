package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PigibagTable")
public class PigibagTable {

    @PrimaryKey(autoGenerate = true)
    private int auto_id;


    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public String getCdtimestamp() {
        return cdtimestamp;
    }

    public void setCdtimestamp(String cdtimestamp) {
        this.cdtimestamp = cdtimestamp;
    }

    @ColumnInfo(name = "cdtimestamp")
    private String cdtimestamp;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @ColumnInfo(name = "user_id")
    private String user_id;
}
