package com.utkarshnew.android.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "APITABLE")
public class APITABLE {

    @PrimaryKey(autoGenerate = true)
    private int auto_id;

    @ColumnInfo(name = "user_id")
    private String user_id;

    @ColumnInfo(name = "timestamp")
    private String timestamp;

    @ColumnInfo(name = "cdtimestamp")
    private String cdtimestamp;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @ColumnInfo(name = "version")
    private String version;


    @ColumnInfo(name = "Apiname")
    private String Apiname;


    @ColumnInfo(name = "Apicode")
    private String Apicode;

    @ColumnInfo(name = "interval")
    private String interval;


    public String getApicode() {
        return Apicode;
    }

    public void setApicode(String apicode) {
        Apicode = apicode;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCdtimestamp() {
        return cdtimestamp;
    }

    public void setCdtimestamp(String cdtimestamp) {
        this.cdtimestamp = cdtimestamp;
    }


    public String getApiname() {
        return Apiname;
    }

    public void setApiname(String apiname) {
        Apiname = apiname;
    }

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }


}
